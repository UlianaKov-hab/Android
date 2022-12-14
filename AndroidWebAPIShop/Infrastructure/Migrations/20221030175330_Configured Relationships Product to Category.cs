using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Infrastructure.Migrations
{
    public partial class ConfiguredRelationshipsProducttoCategory : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "CategoryId",
                table: "tblProducts",
                type: "integer",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_tblProducts_CategoryId",
                table: "tblProducts",
                column: "CategoryId");

            migrationBuilder.AddForeignKey(
                name: "FK_tblProducts_tblCategories_CategoryId",
                table: "tblProducts",
                column: "CategoryId",
                principalTable: "tblCategories",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_tblProducts_tblCategories_CategoryId",
                table: "tblProducts");

            migrationBuilder.DropIndex(
                name: "IX_tblProducts_CategoryId",
                table: "tblProducts");

            migrationBuilder.DropColumn(
                name: "CategoryId",
                table: "tblProducts");
        }
    }
}
