using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace STSDB.Migrations
{
    public partial class addc_projectTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "postCode",
                table: "company");

            migrationBuilder.AddColumn<string>(
                name: "deptName",
                table: "o_user",
                maxLength: 50,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "email",
                table: "company",
                maxLength: 50,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "deptName",
                table: "c_user",
                maxLength: 50,
                nullable: true);

            migrationBuilder.CreateTable(
                name: "c_apply",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    stage = table.Column<string>(maxLength: 50, nullable: true),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_apply", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "c_project",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    pname = table.Column<string>(maxLength: 100, nullable: false),
                    rdTitle = table.Column<string>(maxLength: 50, nullable: false),
                    projectSource = table.Column<int>(nullable: false),
                    formula = table.Column<int>(nullable: false),
                    result = table.Column<int>(nullable: false),
                    targets = table.Column<int>(nullable: false),
                    beginYear = table.Column<int>(nullable: false),
                    endYear = table.Column<int>(nullable: false),
                    beginDate = table.Column<DateTime>(nullable: false),
                    endDate = table.Column<DateTime>(nullable: false),
                    totalBudget = table.Column<int>(nullable: true),
                    selfRaised = table.Column<int>(nullable: true),
                    govFunds = table.Column<int>(nullable: true),
                    outsideFunds = table.Column<int>(nullable: true),
                    otherChannels = table.Column<int>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_project", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "c_project_fund",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    month = table.Column<DateTime>(nullable: false),
                    funds = table.Column<decimal>(type: "decimal(18,2)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_project_fund", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "c_project_year",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    estimateFunds = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    funds = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    reason = table.Column<string>(maxLength: 500, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_project_year", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "c_year_info",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    businessIncome = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    netAssets = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    totalProfit = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    importAndExport = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    rdFiexdAssets = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    equipmentAssets = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    highTechIncome = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    employeeNum = table.Column<int>(nullable: false),
                    rdEmployeeNum = table.Column<int>(nullable: false),
                    rdOrgNum = table.Column<int>(nullable: false),
                    collegeAboveNum = table.Column<int>(nullable: false),
                    patentNum = table.Column<int>(nullable: false),
                    inventionPatentNum = table.Column<int>(nullable: false),
                    validPatentNum = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_year_info", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_c_apply_companyId_projectId_year",
                table: "c_apply",
                columns: new[] { "companyId", "projectId", "year" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_c_project_fund_companyId_projectId_month",
                table: "c_project_fund",
                columns: new[] { "companyId", "projectId", "month" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_c_project_year_companyId_projectId_year",
                table: "c_project_year",
                columns: new[] { "companyId", "projectId", "year" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_c_year_info_companyId_year",
                table: "c_year_info",
                columns: new[] { "companyId", "year" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "c_apply");

            migrationBuilder.DropTable(
                name: "c_project");

            migrationBuilder.DropTable(
                name: "c_project_fund");

            migrationBuilder.DropTable(
                name: "c_project_year");

            migrationBuilder.DropTable(
                name: "c_year_info");

            migrationBuilder.DropColumn(
                name: "deptName",
                table: "o_user");

            migrationBuilder.DropColumn(
                name: "email",
                table: "company");

            migrationBuilder.DropColumn(
                name: "deptName",
                table: "c_user");

            migrationBuilder.AddColumn<string>(
                name: "postCode",
                table: "company",
                maxLength: 10,
                nullable: true);
        }
    }
}
