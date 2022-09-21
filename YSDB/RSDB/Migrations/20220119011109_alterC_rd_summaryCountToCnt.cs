using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterC_rd_summaryCountToCnt : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "proposalCount",
                table: "c_rd_summary",
                newName: "reportCnt");

            migrationBuilder.RenameColumn(
                name: "patentCount",
                table: "c_rd_summary",
                newName: "proposalCnt");

            migrationBuilder.RenameColumn(
                name: "nextRdCount",
                table: "c_rd_summary",
                newName: "patentCnt");

            migrationBuilder.RenameColumn(
                name: "levelFileCount",
                table: "c_rd_summary",
                newName: "nextRdCnt");

            migrationBuilder.RenameColumn(
                name: "lastRdCount",
                table: "c_rd_summary",
                newName: "levelFileCnt");

            migrationBuilder.RenameColumn(
                name: "achievementCount",
                table: "c_rd_summary",
                newName: "lastRdCnt");

            migrationBuilder.AddColumn<int>(
                name: "achievementCnt",
                table: "c_rd_summary",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "achievementCnt",
                table: "c_rd_summary");

            migrationBuilder.RenameColumn(
                name: "reportCnt",
                table: "c_rd_summary",
                newName: "proposalCount");

            migrationBuilder.RenameColumn(
                name: "proposalCnt",
                table: "c_rd_summary",
                newName: "patentCount");

            migrationBuilder.RenameColumn(
                name: "patentCnt",
                table: "c_rd_summary",
                newName: "nextRdCount");

            migrationBuilder.RenameColumn(
                name: "nextRdCnt",
                table: "c_rd_summary",
                newName: "levelFileCount");

            migrationBuilder.RenameColumn(
                name: "levelFileCnt",
                table: "c_rd_summary",
                newName: "lastRdCount");

            migrationBuilder.RenameColumn(
                name: "lastRdCnt",
                table: "c_rd_summary",
                newName: "achievementCount");
        }
    }
}
