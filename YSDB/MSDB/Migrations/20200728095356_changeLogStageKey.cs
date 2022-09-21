using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class changeLogStageKey : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "stageId",
                table: "project_tech_log");

            migrationBuilder.AddColumn<string>(
                name: "stageKey",
                table: "project_tech_log",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "stageKey",
                table: "project_tech_log");

            migrationBuilder.AddColumn<int>(
                name: "stageId",
                table: "project_tech_log",
                nullable: false,
                defaultValue: 0);
        }
    }
}
