using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addFlowinstance_logAddSubmiterAndLastNodeCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "lastNode",
                table: "flowInstance_log",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<int>(
                name: "submiter",
                table: "flowInstance_log",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "lastNode",
                table: "flowInstance_log");

            migrationBuilder.DropColumn(
                name: "submiter",
                table: "flowInstance_log");
        }
    }
}
