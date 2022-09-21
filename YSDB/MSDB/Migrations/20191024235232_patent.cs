using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class patent : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "patent",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    patentNo = table.Column<string>(maxLength: 50, nullable: false),
                    patentName = table.Column<string>(maxLength: 200, nullable: true),
                    applyDateTime = table.Column<DateTime>(nullable: false),
                    mainType = table.Column<string>(maxLength: 100, nullable: true),
                    mainTypeNo = table.Column<string>(maxLength: 100, nullable: true),
                    caseStatus = table.Column<string>(maxLength: 50, nullable: true),
                    caseSubmissionDate = table.Column<DateTime>(nullable: false),
                    inventor = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_patent", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "patent_apply",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    patentNo = table.Column<string>(maxLength: 50, nullable: false),
                    companyName = table.Column<string>(maxLength: 50, nullable: false),
                    companyAddress = table.Column<string>(maxLength: 100, nullable: true),
                    companyId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_patent_apply", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "patent_cost",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    patentNo = table.Column<string>(maxLength: 50, nullable: false),
                    costType = table.Column<string>(maxLength: 200, nullable: false),
                    limitDate = table.Column<DateTime>(nullable: false),
                    amount = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    isPay = table.Column<bool>(nullable: false),
                    payDateTime = table.Column<DateTime>(nullable: false),
                    isRemind = table.Column<bool>(nullable: false),
                    remindDateTime = table.Column<DateTime>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_patent_cost", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "patent");

            migrationBuilder.DropTable(
                name: "patent_apply");

            migrationBuilder.DropTable(
                name: "patent_cost");
        }
    }
}
