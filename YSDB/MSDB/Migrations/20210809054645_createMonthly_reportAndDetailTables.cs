using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createMonthly_reportAndDetailTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "monthly_report",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    userId = table.Column<int>(nullable: false),
                    workMonth = table.Column<DateTime>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    updateTime = table.Column<DateTime>(nullable: false),
                    done = table.Column<int>(nullable: false),
                    commit = table.Column<int>(nullable: false),
                    reject = table.Column<int>(nullable: false),
                    serviceCnt = table.Column<int>(nullable: false),
                    companyCnt = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_monthly_report", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "monthly_report_detail",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    userId = table.Column<int>(nullable: false),
                    workMonth = table.Column<DateTime>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    updateTime = table.Column<DateTime>(nullable: false),
                    done = table.Column<int>(nullable: false),
                    commit = table.Column<int>(nullable: false),
                    reject = table.Column<int>(nullable: false),
                    moduleId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_monthly_report_detail", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_monthly_report_userId_workMonth",
                table: "monthly_report",
                columns: new[] { "userId", "workMonth" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_monthly_report_detail_userId_workMonth_moduleId",
                table: "monthly_report_detail",
                columns: new[] { "userId", "workMonth", "moduleId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "monthly_report");

            migrationBuilder.DropTable(
                name: "monthly_report_detail");
        }
    }
}
