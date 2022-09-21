using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class changeAuditPassColToStatus : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "pass",
                table: "p_audit");

            migrationBuilder.AddColumn<int>(
                name: "status",
                table: "p_audit",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "status",
                table: "p_audit");

            migrationBuilder.AddColumn<bool>(
                name: "pass",
                table: "p_audit",
                nullable: false,
                defaultValue: false);
        }
    }
}
