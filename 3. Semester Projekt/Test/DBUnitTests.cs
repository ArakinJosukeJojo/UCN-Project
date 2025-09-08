using System;
using Moq;
using Xunit;
using Microsoft.Data.SqlClient;
using DataAccess.DatabaseLayer;
using System.Data;
using DataAccess.ModelLayer;
using Xunit.Abstractions;
using Test;

namespace DBTests
{
    public class DBUnitTests : IClassFixture<SqlScriptFixture>
    {
        private readonly SqlScriptFixture _fixture;
        private readonly ITestOutputHelper _output;

        public DBUnitTests(ITestOutputHelper output, SqlScriptFixture fixture)
        {
            _output = output;
            _fixture = fixture;
        }

        [Fact]
        public void Test_ConnectionIsOpen()
        {
            // Arrange
            var connection = _fixture.GetConnection();

            // Act

            // Assert
            Assert.Equal(System.Data.ConnectionState.Open, connection.State);
        }

        [Fact]
        public void Test_ConnectionCanExecuteQuery()
        {
            // Arrange
            var connection = _fixture.GetConnection();
            int result = 1;

            // Act
            using (var command = new SqlCommand("SELECT 1", connection))
            {
                result = Convert.ToInt32(command.ExecuteScalar());
            }

            //Assert
            Assert.Equal(1, result);

        }
    }
}
