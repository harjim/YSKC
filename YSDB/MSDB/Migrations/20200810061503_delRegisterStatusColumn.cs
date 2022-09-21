using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class delRegisterStatusColumn : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "changeStatus",
                table: "project_register");

            migrationBuilder.DropColumn(
                name: "dockStatus",
                table: "project_register");

            migrationBuilder.DropColumn(
                name: "reportStatus",
                table: "project_register");

            migrationBuilder.DropColumn(
                name: "schemeStatus",
                table: "project_register");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "changeStatus",
                table: "project_register",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "dockStatus",
                table: "project_register",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "reportStatus",
                table: "project_register",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "schemeStatus",
                table: "project_register",
                nullable: true);
        }
    }
}
