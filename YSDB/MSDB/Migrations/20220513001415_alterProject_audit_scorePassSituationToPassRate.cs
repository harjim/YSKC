using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterProject_audit_scorePassSituationToPassRate : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "passSituation",
                table: "project_audit_score",
                newName: "passRate");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "passRate",
                table: "project_audit_score",
                newName: "passSituation");
        }
    }
}
