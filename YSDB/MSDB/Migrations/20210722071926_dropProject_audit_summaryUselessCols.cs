using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class dropProject_audit_summaryUselessCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
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

            migrationBuilder.DropColumn(
                name: "ongoingTotal",
                table: "project_audit_summary");

            migrationBuilder.DropColumn(
                name: "reportDoneCnt",
                table: "project_audit_summary");

            migrationBuilder.DropColumn(
                name: "reportOngoingCnt",
                table: "project_audit_summary");

            migrationBuilder.AlterColumn<int>(
                name: "projectId",
                table: "project_audit_summary",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<int>(
                name: "customerId",
                table: "project_audit_summary",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "year",
                table: "project_audit_summary",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "customerId",
                table: "project_audit_summary");

            migrationBuilder.DropColumn(
                name: "year",
                table: "project_audit_summary");

            migrationBuilder.AlterColumn<int>(
                name: "projectId",
                table: "project_audit_summary",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AddColumn<int>(
                name: "docDoneCnt",
                table: "project_audit_summary",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "docOngoingCnt",
                table: "project_audit_summary",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "doneTotal",
                table: "project_audit_summary",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "ongoingTotal",
                table: "project_audit_summary",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "reportDoneCnt",
                table: "project_audit_summary",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "reportOngoingCnt",
                table: "project_audit_summary",
                nullable: true);
        }
    }
}
