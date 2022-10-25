using AndroidWebAPIShop.Models;
using AutoMapper;
using Infrastructure;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace AndroidWebAPIShop.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CategoriesController : ControllerBase
    {
        private readonly ShopEFContext _contect;
        private readonly IMapper _mapper;
        public CategoriesController(ShopEFContext context, IMapper mapper)
        {
            _contect = context;
            _mapper = mapper;
        }

        [HttpGet]
        [Route("list")]
        public async Task<IActionResult> Index()
        {
            var model = _contect.Categories
                .Select(x => _mapper.Map<CategoryItemModel>(x))
                .ToList();

            return Ok(model);
        }
    }
}
