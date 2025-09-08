using Newtonsoft.Json;

namespace DesktopClient.ServiceLayer;

public abstract class ServiceConnection
{

    private readonly HttpClient _httpEnabler;

    public ServiceConnection(string inBaseUrl)
    {
        _httpEnabler = new HttpClient();
        BaseUrl = inBaseUrl;
        UseUrl = BaseUrl;
    }

    public string? BaseUrl { get; init; }
    public string? UseUrl { get; set; }

    public HttpResponseMessage? CallServiceGet()
    {
        HttpResponseMessage? hrm = null;
        if (UseUrl != null)
        {
            hrm = _httpEnabler.GetAsync(UseUrl).Result;
        }
        return hrm;

    }
    public HttpResponseMessage? CallServicePost(StringContent postJson)
    {
        HttpResponseMessage? hrm = null;
        if (UseUrl != null)
        {
            hrm = _httpEnabler.PostAsync(UseUrl, postJson).Result;
        }
        return hrm;
    }
    public HttpResponseMessage? CallServiceDelete()
    {

        HttpResponseMessage? hrm = null;
        if (UseUrl != null)
        {
            hrm = _httpEnabler.DeleteAsync(UseUrl).Result;
               
        }
        return hrm;
    }
    public HttpResponseMessage? CallServicePut(StringContent postJson)
    {
        HttpResponseMessage? hrm = null;
        if (UseUrl != null)
        {
            hrm = _httpEnabler.PutAsync(UseUrl, postJson).Result;
        }
        return hrm;
    }
}
