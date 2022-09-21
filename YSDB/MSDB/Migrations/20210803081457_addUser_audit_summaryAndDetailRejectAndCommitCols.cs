using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addUser_audit_summaryAndDetailRejectAndCommitCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "ongoing",
                table: "user_audit_summary",
                newName: "reject");

            migrationBuilder.RenameColumn(
                name: "ongoing",
                table: "user_audit_detail",
                newName: "reject");

            migrationBuilder.AddColumn<int>(
                name: "commit",
                table: "user_audit_summary",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "commit",
                table: "user_audit_detail",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "commit",
                table: "user_audit_summary");

            migrationBuilder.DropColumn(
                name: "commit",
                table: "user_audit_detail");

            migrationBuilder.RenameColumn(
                name: "reject",
                table: "user_audit_summary",
                newName: "ongoing");

            migrationBuilder.RenameColumn(
                name: "reject",
                table: "user_audit_detail",
                newName: "ongoing");
        }
    }
}
