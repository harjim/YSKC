using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createBranch_summaryTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "branch_summary",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    rootDeptId = table.Column<int>(nullable: false),
                    deptId = table.Column<int>(nullable: false),
                    customerCount = table.Column<int>(nullable: false),
                    rdCount = table.Column<int>(nullable: false),
                    rdFundSum = table.Column<decimal>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    updateTime = table.Column<DateTime>(nullable: false),
                    year = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_branch_summary", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_branch_summary_deptId_year",
                table: "branch_summary",
                columns: new[] { "deptId", "year" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "branch_summary");
        }
    }
}
