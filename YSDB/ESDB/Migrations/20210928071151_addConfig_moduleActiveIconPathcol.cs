using Microsoft.EntityFrameworkCore.Migrations;

namespace ESDB.Migrations
{
    public partial class addConfig_moduleActiveIconPathcol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "activeIconPath",
                table: "config_module",
                maxLength: 200,
                nullable: false,
                defaultValue: "");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "activeIconPath",
                table: "config_module");
        }
    }
}
