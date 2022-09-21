using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class movePatentsTableToRSDB : Migration
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
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    patentNo = table.Column<string>(maxLength: 50, nullable: false),
                    patentName = table.Column<string>(maxLength: 200, nullable: true),
                    applyDateTime = table.Column<DateTime>(nullable: true),
                    mainType = table.Column<string>(maxLength: 100, nullable: true),
                    mainTypeNo = table.Column<string>(maxLength: 100, nullable: true),
                    caseStatus = table.Column<string>(maxLength: 50, nullable: true),
                    caseSubmissionDate = table.Column<DateTime>(nullable: true),
                    inventor = table.Column<string>(maxLength: 200, nullable: true),
                    expiryDate = table.Column<DateTime>(nullable: true),
                    expiryAmount = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    companyId = table.Column<int>(nullable: true),
                    year = table.Column<int>(nullable: true),
                    projectId = table.Column<int>(nullable: true)
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
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    patentNo = table.Column<string>(maxLength: 50, nullable: false),
                    applyName = table.Column<string>(maxLength: 50, nullable: false),
                    address = table.Column<string>(maxLength: 100, nullable: true),
                    customerId = table.Column<int>(nullable: true)
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
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    patentNo = table.Column<string>(maxLength: 50, nullable: false),
                    costType = table.Column<string>(maxLength: 200, nullable: false),
                    limitDate = table.Column<DateTime>(nullable: true),
                    amount = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    isPay = table.Column<bool>(nullable: false),
                    payDateTime = table.Column<DateTime>(nullable: true),
                    isRemind = table.Column<bool>(nullable: false),
                    remindDateTime = table.Column<DateTime>(nullable: true),
                    remark = table.Column<string>(maxLength: 200, nullable: true),
                    receiptNo = table.Column<string>(maxLength: 50, nullable: true),
                    status = table.Column<string>(maxLength: 20, nullable: true),
                    payer = table.Column<string>(maxLength: 100, nullable: true)
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
