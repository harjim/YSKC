using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addPorjectBasicAgain : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "project_basic",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    isImplemented = table.Column<int>(nullable: true),
                    income = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    incomeTax = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    totalStaff = table.Column<int>(nullable: true),
                    researchFee = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    taxRefiefs = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    techStaff = table.Column<string>(maxLength: 200, nullable: true),
                    financeStaff = table.Column<string>(maxLength: 200, nullable: true),
                    manager = table.Column<string>(maxLength: 200, nullable: true),
                    startTime = table.Column<DateTime>(nullable: true),
                    applyStatus = table.Column<int>(nullable: true),
                    hasPayPatent = table.Column<bool>(nullable: true),
                    other = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_project_basic", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "project_basic");
        }
    }
}
