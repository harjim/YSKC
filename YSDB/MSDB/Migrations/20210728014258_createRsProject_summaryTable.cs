using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createRsProject_summaryTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "rsProject_summary",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    rsProjectId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    memberCnt = table.Column<int>(nullable: true),
                    equipmentCnt = table.Column<int>(nullable: true),
                    patentCnt = table.Column<int>(nullable: true),
                    hasReport = table.Column<bool>(nullable: true),
                    reportStatus = table.Column<int>(nullable: true),
                    hasNewReport = table.Column<bool>(nullable: true),
                    newReportStatus = table.Column<int>(nullable: true),
                    docTotal = table.Column<int>(nullable: true),
                    docSubmitCnt = table.Column<int>(nullable: true),
                    docDoneCnt = table.Column<int>(nullable: true),
                    backupDataTotalCnt = table.Column<int>(nullable: true),
                    createTime = table.Column<DateTime>(nullable: false),
                    updateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_rsProject_summary", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_rsProject_summary_companyId_year_rsProjectId",
                table: "rsProject_summary",
                columns: new[] { "companyId", "year", "rsProjectId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "rsProject_summary");
        }
    }
}
