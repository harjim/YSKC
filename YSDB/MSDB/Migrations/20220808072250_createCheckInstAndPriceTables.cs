using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createCheckInstAndPriceTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "checkInst",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    instName = table.Column<string>(maxLength: 50, nullable: false),
                    accountName = table.Column<string>(maxLength: 50, nullable: false),
                    accountBank = table.Column<string>(maxLength: 50, nullable: false),
                    accountNo = table.Column<string>(maxLength: 50, nullable: false),
                    postage = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    payRemark = table.Column<string>(maxLength: 500, nullable: true),
                    remark = table.Column<string>(maxLength: 200, nullable: true),
                    linkMan = table.Column<string>(maxLength: 20, nullable: true),
                    linkTel = table.Column<string>(maxLength: 20, nullable: true),
                    filePath = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_checkInst", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "checkInst_price",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    checkInstId = table.Column<int>(nullable: false),
                    days = table.Column<int>(nullable: false),
                    amount = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    checkType = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_checkInst_price", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "checkInst");

            migrationBuilder.DropTable(
                name: "checkInst_price");
        }
    }
}
