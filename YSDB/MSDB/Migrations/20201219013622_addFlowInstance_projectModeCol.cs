using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addFlowInstance_projectModeCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_flowInstance_project_companyId_year_projectId",
                table: "flowInstance_project");

            migrationBuilder.DropColumn(
                name: "mode",
                table: "flowInstance");

            migrationBuilder.AddColumn<int>(
                name: "mode",
                table: "flowInstance_project",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_flowInstance_project_companyId_year_projectId_mode",
                table: "flowInstance_project",
                columns: new[] { "companyId", "year", "projectId", "mode" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_flowInstance_project_companyId_year_projectId_mode",
                table: "flowInstance_project");

            migrationBuilder.DropColumn(
                name: "mode",
                table: "flowInstance_project");

            migrationBuilder.AddColumn<int>(
                name: "mode",
                table: "flowInstance",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_flowInstance_project_companyId_year_projectId",
                table: "flowInstance_project",
                columns: new[] { "companyId", "year", "projectId" },
                unique: true);
        }
    }
}
