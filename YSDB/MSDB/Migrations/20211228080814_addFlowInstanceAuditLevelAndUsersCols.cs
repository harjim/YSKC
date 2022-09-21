using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addFlowInstanceAuditLevelAndUsersCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "auditLevel",
                table: "flowInstance",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "auditUsers",
                table: "flowInstance",
                maxLength: 200,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "auditLevel",
                table: "flowInstance");

            migrationBuilder.DropColumn(
                name: "auditUsers",
                table: "flowInstance");
        }
    }
}
