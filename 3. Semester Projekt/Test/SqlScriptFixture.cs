using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using Microsoft.Data.SqlClient;
using Microsoft.Extensions.Configuration;

namespace Test
{
    public class SqlScriptFixture : IAsyncLifetime
    {
        private readonly string _masterConnectionString;
        private readonly string _beforeScript1 = "Scripts/CreateTables.sql";
        private readonly string _beforeScript2 = "Scripts/HotelBooking_TestData.sql";
        private readonly string _testDatabaseName;
        private string _testDatabaseConnectionString;
        private SqlConnection _connection;

        public SqlScriptFixture()
        {
            var config = LoadConfiguration();
            _masterConnectionString = config.GetConnectionString("DatabaseConnection")
                                ?? throw new InvalidOperationException("Master Connection string not found.");

            _testDatabaseName = $"HotelBooking_Test_{Guid.NewGuid().ToString("N").Substring(0, 8)}";
            _testDatabaseConnectionString = new SqlConnectionStringBuilder(_masterConnectionString)
            {
                InitialCatalog = _testDatabaseName
            }.ConnectionString;
        }
        private static IConfiguration LoadConfiguration()
        {
            return new ConfigurationBuilder()
                .SetBasePath(AppContext.BaseDirectory)
                .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
                .Build();
        }
        public string GetConnectionString() => _testDatabaseConnectionString;

        public SqlConnection GetConnection() => _connection;
        public async Task InitializeAsync()
        {
            {
                await using var masterConnection = new SqlConnection(_masterConnectionString);
                await masterConnection.OpenAsync();

                var createDbCommand = $"CREATE DATABASE [{_testDatabaseName}]";
                await using (var cmd = new SqlCommand(createDbCommand, masterConnection))
                {
                    await cmd.ExecuteNonQueryAsync();
                }

                _connection = new SqlConnection(_testDatabaseConnectionString);
                await _connection.OpenAsync();

                await ExecuteScriptAsync(_beforeScript1);
                await ExecuteScriptAsync(_beforeScript2);
            }
        }

        public async Task DisposeAsync()
        {

            if (_connection?.State == System.Data.ConnectionState.Open)
            {
                await _connection.CloseAsync();
                _connection.Dispose();
            }

            await using var masterConnection = new SqlConnection(_masterConnectionString);
            await masterConnection.OpenAsync();

            var killConnectionsCmd = $@"
                ALTER DATABASE [{_testDatabaseName}] SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
                DROP DATABASE [{_testDatabaseName}];
            ";

            await using (var cmd = new SqlCommand(killConnectionsCmd, masterConnection))
            {
                await cmd.ExecuteNonQueryAsync();
            }
        }
            
        private async Task ExecuteScriptAsync(string path)
        {
            var fullPath = Path.Combine(AppContext.BaseDirectory, path);

            if (!File.Exists(fullPath))
                throw new FileNotFoundException($"SQL script not found: {fullPath}");

            var script = await File.ReadAllTextAsync(fullPath);

            var batches = Regex.Split(script, @"^\s*GO\s*$", RegexOptions.Multiline | RegexOptions.IgnoreCase);

            foreach (var batch in batches)
            {
                var trimmed = batch.Trim();
                if (string.IsNullOrWhiteSpace(trimmed))
                    continue;

                using var command = new SqlCommand(trimmed, _connection);
                await command.ExecuteNonQueryAsync();
            }
        }
    }
}
