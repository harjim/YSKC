using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addCapitalUnit : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "capitalUnit",
                table: "customer",
                maxLength: 50,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "remark",
                table: "customer",
                maxLength: 200,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "capitalUnit",
                table: "customer");

            migrationBuilder.DropColumn(
                name: "remark",
                table: "customer");
        }
    }
}
