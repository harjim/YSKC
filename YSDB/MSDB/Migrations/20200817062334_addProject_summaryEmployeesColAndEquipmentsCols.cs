using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addProject_summaryEmployeesColAndEquipmentsCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "employeeCount",
                table: "project_summary",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "equipmentCount",
                table: "project_summary",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "rdEmployeeCount",
                table: "project_summary",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "rdEquipmentCount",
                table: "project_summary",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "employeeCount",
                table: "project_summary");

            migrationBuilder.DropColumn(
                name: "equipmentCount",
                table: "project_summary");

            migrationBuilder.DropColumn(
                name: "rdEmployeeCount",
                table: "project_summary");

            migrationBuilder.DropColumn(
                name: "rdEquipmentCount",
                table: "project_summary");
        }
    }
}
