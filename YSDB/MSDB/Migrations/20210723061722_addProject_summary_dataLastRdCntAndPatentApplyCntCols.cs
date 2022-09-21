using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addProject_summary_dataLastRdCntAndPatentApplyCntCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "lastRdCnt",
                table: "project_summary_data",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "patentApplyCnt",
                table: "project_summary_data",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "lastRdCnt",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "patentApplyCnt",
                table: "project_summary_data");
        }
    }
}
