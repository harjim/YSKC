using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class auditSummaryAddCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "reportDoneCnt",
                table: "project_audit_summary",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "reportOngoingCnt",
                table: "project_audit_summary",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "reportDoneCnt",
                table: "project_audit_summary");

            migrationBuilder.DropColumn(
                name: "reportOngoingCnt",
                table: "project_audit_summary");
        }
    }
}
