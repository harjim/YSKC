using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createPatent_plan_infoTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "patent_plan_info",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    patentPlanId = table.Column<int>(nullable: false),
                    submittedDate = table.Column<DateTime>(nullable: true),
                    acceptNoticeDate = table.Column<DateTime>(nullable: true),
                    acceptFeeDate = table.Column<DateTime>(nullable: true),
                    acceptFee = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    authDate = table.Column<DateTime>(nullable: true),
                    authFeeDate = table.Column<DateTime>(nullable: true),
                    authFee = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    issueDate = table.Column<DateTime>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_patent_plan_info", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_patent_plan_info_patentPlanId",
                table: "patent_plan_info",
                column: "patentPlanId",
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "patent_plan_info");
        }
    }
}
