using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class _101 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "fullPath",
                table: "ys_dept",
                maxLength: 300,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "fullPath",
                table: "app_menu",
                maxLength: 300,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "fullPath",
                table: "ys_dept");

            migrationBuilder.DropColumn(
                name: "fullPath",
                table: "app_menu");
        }
    }
}
