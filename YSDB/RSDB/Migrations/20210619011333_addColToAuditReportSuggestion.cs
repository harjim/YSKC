using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addColToAuditReportSuggestion : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "suggestion",
                table: "p_audit_report",
                maxLength: 500,
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_p_audit_report_projectId_moduleId",
                table: "p_audit_report",
                columns: new[] { "projectId", "moduleId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_p_audit_report_projectId_moduleId",
                table: "p_audit_report");

            migrationBuilder.DropColumn(
                name: "suggestion",
                table: "p_audit_report");
        }
    }
}
