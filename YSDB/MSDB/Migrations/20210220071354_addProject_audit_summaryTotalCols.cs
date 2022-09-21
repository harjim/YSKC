using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addProject_audit_summaryTotalCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "rejectCnt",
                table: "project_audit_summary",
                newName: "ongoingTotal");

            migrationBuilder.AddColumn<int>(
                name: "docDoneCnt",
                table: "project_audit_summary",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "docOngoingCnt",
                table: "project_audit_summary",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "doneTotal",
                table: "project_audit_summary",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "docDoneCnt",
                table: "project_audit_summary");

            migrationBuilder.DropColumn(
                name: "docOngoingCnt",
                table: "project_audit_summary");

            migrationBuilder.DropColumn(
                name: "doneTotal",
                table: "project_audit_summary");

            migrationBuilder.RenameColumn(
                name: "ongoingTotal",
                table: "project_audit_summary",
                newName: "rejectCnt");
        }
    }
}
