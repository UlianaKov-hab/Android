using AndroidWebAPIShop.Helpers;
using AndroidWebAPIShop.Models;
using ApplicationCore.Entities;
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

        [HttpPost]
        [Route("create")]
        public async Task<IActionResult> Create(CategoryCreateModel model)
        {
            try
            {
                var category = _mapper.Map<CategoryEntity>(model);
                category.Image = ImageWorker.SaveImage(model.Image);
                await _contect.Categories.AddAsync(category);
                await _contect.SaveChangesAsync();

            }
            catch(Exception ex)
            {
                return BadRequest(new { error = ex.Message });
            }
            return Ok();
        }
    }
}
