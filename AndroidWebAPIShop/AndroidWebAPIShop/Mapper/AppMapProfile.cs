using AndroidWebAPIShop.Models;
using ApplicationCore.Entities;
using AutoMapper;

namespace AndroidWebAPIShop.Mapper
{
    public class AppMapProfile : Profile
    {
        public AppMapProfile()
        {
            CreateMap<CategoryEntity, CategoryItemModel>()
                .ForMember(x => x.Image, opt => opt.MapFrom(x => $"/images/{x.Image}"));
        }
    }
}
