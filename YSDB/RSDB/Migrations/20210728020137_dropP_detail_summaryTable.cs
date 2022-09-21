using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class dropP_detail_summaryTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_detail_summary");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "p_detail_summary",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    backupDataTotalCnt = table.Column<int>(nullable: true),
                    companyId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    docDoneCnt = table.Column<int>(nullable: true),
                    docSubmitCnt = table.Column<int>(nullable: true),
                    docTotal = table.Column<int>(nullable: true),
                    equipmentCnt = table.Column<int>(nullable: true),
                    hasNewReport = table.Column<bool>(nullable: true),
                    hasReport = table.Column<bool>(nullable: true),
                    memberCnt = table.Column<int>(nullable: true),
                    newReportStatus = table.Column<int>(nullable: true),
                    patentCnt = table.Column<int>(nullable: true),
                    projectId = table.Column<int>(nullable: false),
                    reportStatus = table.Column<int>(nullable: true),
                    updateTime = table.Column<DateTime>(nullable: false),
                    year = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_detail_summary", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_p_detail_summary_companyId_year_projectId",
                table: "p_detail_summary",
                columns: new[] { "companyId", "year", "projectId" },
                unique: true);
        }
    }
}
