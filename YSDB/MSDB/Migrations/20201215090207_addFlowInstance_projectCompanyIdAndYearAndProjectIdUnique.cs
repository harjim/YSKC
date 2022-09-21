using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addFlowInstance_projectCompanyIdAndYearAndProjectIdUnique : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_flowInstance_project_companyId_year_projectId",
                table: "flowInstance_project",
                columns: new[] { "companyId", "year", "projectId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_flowInstance_project_companyId_year_projectId",
                table: "flowInstance_project");
        }
    }
}
