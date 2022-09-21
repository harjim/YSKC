using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class deleteBranchSummaryAndAddDeptBranchIdCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "branch_summary");

            migrationBuilder.AddColumn<int>(
                name: "branchId",
                table: "ys_dept",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "branchId",
                table: "ys_dept");

            migrationBuilder.CreateTable(
                name: "branch_summary",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    branchDeptId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    customerCount = table.Column<int>(nullable: false),
                    deptId = table.Column<int>(nullable: false),
                    rdCount = table.Column<int>(nullable: false),
                    rdFundsSum = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    updateTime = table.Column<DateTime>(nullable: false),
                    year = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_branch_summary", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_branch_summary_deptId_year_branchDeptId",
                table: "branch_summary",
                columns: new[] { "deptId", "year", "branchDeptId" },
                unique: true);
        }
    }
}
