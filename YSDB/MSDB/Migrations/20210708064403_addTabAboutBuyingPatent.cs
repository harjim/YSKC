using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addTabAboutBuyingPatent : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "patent_buying_demand",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    customerId = table.Column<int>(nullable: false),
                    status = table.Column<int>(nullable: false),
                    filePath = table.Column<string>(maxLength: 1000, nullable: true),
                    remark = table.Column<string>(maxLength: 500, nullable: true),
                    inventionNum = table.Column<int>(nullable: true),
                    utilityNum = table.Column<int>(nullable: true),
                    appearanceDesignNum = table.Column<int>(nullable: true),
                    inventorInfo = table.Column<string>(maxLength: 1000, nullable: true),
                    ownerId = table.Column<int>(nullable: true),
                    submitTime = table.Column<DateTime>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_patent_buying_demand", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "patent_buying_list",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    filePath = table.Column<string>(maxLength: 1000, nullable: true),
                    customerId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_patent_buying_list", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "patent_sea",
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
                    applyDateTime = table.Column<DateTime>(nullable: true),
                    inventor = table.Column<string>(maxLength: 200, nullable: true),
                    mainType = table.Column<int>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_patent_sea", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "patent_buying_demand");

            migrationBuilder.DropTable(
                name: "patent_buying_list");

            migrationBuilder.DropTable(
                name: "patent_sea");
        }
    }
}
