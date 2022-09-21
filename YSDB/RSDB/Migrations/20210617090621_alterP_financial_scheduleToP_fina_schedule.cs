using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterP_financial_scheduleToP_fina_schedule : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_financial_schedule");

            migrationBuilder.CreateTable(
                name: "p_fina_schedule",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    month = table.Column<DateTime>(nullable: false),
                    workHours = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    testHour = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    trialRdHour = table.Column<decimal>(type: "decimal(18,2)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_fina_schedule", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_p_fina_schedule_companyId_projectId_month",
                table: "p_fina_schedule",
                columns: new[] { "companyId", "projectId", "month" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_fina_schedule");

            migrationBuilder.CreateTable(
                name: "p_financial_schedule",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    month = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    rdHour = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    type = table.Column<int>(nullable: false),
                    workHours = table.Column<decimal>(type: "decimal(18,2)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_financial_schedule", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_p_financial_schedule_companyId_projectId_type_month",
                table: "p_financial_schedule",
                columns: new[] { "companyId", "projectId", "type", "month" },
                unique: true);
        }
    }
}
