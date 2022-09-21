using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterDocFileTemplateTrustToType : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "trust",
                table: "docFileTemplate");

            migrationBuilder.AddColumn<int>(
                name: "type",
                table: "docFileTemplate",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "type",
                table: "docFileTemplate");

            migrationBuilder.AddColumn<bool>(
                name: "trust",
                table: "docFileTemplate",
                nullable: false,
                defaultValue: false);
        }
    }
}
