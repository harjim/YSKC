using Microsoft.EntityFrameworkCore.Migrations;

namespace ESDB.Migrations
{
    public partial class addArticleTopCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "top",
                table: "article",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "top",
                table: "article");
        }
    }
}
