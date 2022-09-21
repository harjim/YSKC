using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace STSDB.Migrations
{
    public partial class alterTableNamep_project_fundToFunds : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "c_project_fund");

            migrationBuilder.CreateTable(
                name: "c_project_funds",
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
                    table.PrimaryKey("PK_c_project_funds", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_c_project_funds_companyId_projectId_month",
                table: "c_project_funds",
                columns: new[] { "companyId", "projectId", "month" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "c_project_funds");

            migrationBuilder.CreateTable(
                name: "c_project_fund",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    funds = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    month = table.Column<DateTime>(nullable: false),
                    projectId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_project_fund", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_c_project_fund_companyId_projectId_month",
                table: "c_project_fund",
                columns: new[] { "companyId", "projectId", "month" },
                unique: true);
        }
    }
}
