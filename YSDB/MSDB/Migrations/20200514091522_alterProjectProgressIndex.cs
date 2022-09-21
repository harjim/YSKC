using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterProjectProgressIndex : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_project_progress_detail_companyId_stage_year",
                table: "project_progress_detail",
                columns: new[] { "companyId", "stage", "year" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_project_progress_companyId_stage_year",
                table: "project_progress",
                columns: new[] { "companyId", "stage", "year" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_project_progress_detail_companyId_stage_year",
                table: "project_progress_detail");

            migrationBuilder.DropIndex(
                name: "IX_project_progress_companyId_stage_year",
                table: "project_progress");
        }
    }
}
