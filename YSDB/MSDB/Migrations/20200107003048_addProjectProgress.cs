using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addProjectProgress : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "FinanceStatus",
                table: "project",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "artisanStatus",
                table: "project",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<string>(
                name: "describe",
                table: "project",
                maxLength: 200,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "status",
                table: "project",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "FinanceStatus",
                table: "project");

            migrationBuilder.DropColumn(
                name: "artisanStatus",
                table: "project");

            migrationBuilder.DropColumn(
                name: "describe",
                table: "project");

            migrationBuilder.DropColumn(
                name: "status",
                table: "project");
        }
    }
}
