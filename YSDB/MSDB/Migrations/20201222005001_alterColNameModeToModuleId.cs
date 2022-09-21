using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterColNameModeToModuleId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "mode",
                table: "flowInstance_project",
                newName: "moduleId");

            migrationBuilder.RenameIndex(
                name: "IX_flowInstance_project_companyId_year_projectId_mode",
                table: "flowInstance_project",
                newName: "IX_flowInstance_project_companyId_year_projectId_moduleId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "moduleId",
                table: "flowInstance_project",
                newName: "mode");

            migrationBuilder.RenameIndex(
                name: "IX_flowInstance_project_companyId_year_projectId_moduleId",
                table: "flowInstance_project",
                newName: "IX_flowInstance_project_companyId_year_projectId_mode");
        }
    }
}
