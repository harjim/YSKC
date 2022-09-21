using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterProject_audit_summaryOngingCntToOngoingCnt : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "ongingCnt",
                table: "project_audit_summary",
                newName: "ongoingCnt");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "ongoingCnt",
                table: "project_audit_summary",
                newName: "ongingCnt");
        }
    }
}
