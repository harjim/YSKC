using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterProject_audit_summaryProjectIdAndModeTypeUniqueIndex : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_project_audit_summary_projectId",
                table: "project_audit_summary");

            migrationBuilder.CreateIndex(
                name: "IX_project_audit_summary_projectId_modeType",
                table: "project_audit_summary",
                columns: new[] { "projectId", "modeType" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_project_audit_summary_projectId_modeType",
                table: "project_audit_summary");

            migrationBuilder.CreateIndex(
                name: "IX_project_audit_summary_projectId",
                table: "project_audit_summary",
                column: "projectId",
                unique: true);
        }
    }
}
