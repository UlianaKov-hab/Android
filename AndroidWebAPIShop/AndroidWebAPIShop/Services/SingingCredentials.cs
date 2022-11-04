using Microsoft.IdentityModel.Tokens;

namespace AndroidWebAPIShop.Services
{
    internal class SingingCredentials
    {
        private SymmetricSecurityKey singinKey;
        private string hmacSha256;

        public SingingCredentials(SymmetricSecurityKey singinKey, string hmacSha256)
        {
            this.singinKey = singinKey;
            this.hmacSha256 = hmacSha256;
        }
    }
}