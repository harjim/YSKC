using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createP_detail_summaryTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "p_detail_summary",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
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
                    backupDataTotalCnt = table.Column<int>(nullable: true)
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

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_detail_summary");
        }
    }
}
