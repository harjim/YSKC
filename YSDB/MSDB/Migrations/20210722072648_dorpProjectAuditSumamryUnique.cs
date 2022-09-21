using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class dorpProjectAuditSumamryUnique : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_project_audit_summary_projectId_modeType",
                table: "project_audit_summary");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_project_audit_summary_projectId_modeType",
                table: "project_audit_summary",
                columns: new[] { "projectId", "modeType" },
                unique: true);
        }
    }
}
