using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addFlowInstance_userStatusCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "mode",
                table: "flowNode",
                newName: "auditType");

            migrationBuilder.AddColumn<bool>(
                name: "status",
                table: "flowInstance_user",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "status",
                table: "flowInstance_user");

            migrationBuilder.RenameColumn(
                name: "auditType",
                table: "flowNode",
                newName: "mode");
        }
    }
}
