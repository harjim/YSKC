using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class delTabDocFileUnusedCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "seq",
                table: "docFile");

            migrationBuilder.DropColumn(
                name: "templateName",
                table: "docFile");

            migrationBuilder.DropColumn(
                name: "templatePath",
                table: "docFile");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "seq",
                table: "docFile",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "templateName",
                table: "docFile",
                maxLength: 30,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "templatePath",
                table: "docFile",
                maxLength: 30,
                nullable: true);
        }
    }
}
