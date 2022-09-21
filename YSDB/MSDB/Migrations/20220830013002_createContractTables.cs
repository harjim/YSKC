using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createContractTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "contract",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    customerId = table.Column<int>(nullable: false),
                    deptId = table.Column<int>(nullable: false),
                    projectCnt = table.Column<int>(nullable: false),
                    partnerCnt = table.Column<int>(nullable: false),
                    signDate = table.Column<DateTime>(nullable: false),
                    effectDate = table.Column<DateTime>(nullable: false),
                    closeDate = table.Column<DateTime>(nullable: false),
                    techId = table.Column<int>(nullable: false),
                    finaId = table.Column<int>(nullable: false),
                    filepath = table.Column<string>(maxLength: 500, nullable: true),
                    remark = table.Column<string>(maxLength: 200, nullable: true),
                    sealType = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_contract", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "contract_member",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    contractId = table.Column<int>(nullable: false),
                    mtype = table.Column<int>(nullable: false),
                    userId = table.Column<int>(nullable: false),
                    ratio = table.Column<decimal>(type: "decimal(5,2)", nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_contract_member", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "contract_project",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    contractId = table.Column<int>(nullable: false),
                    customerId = table.Column<int>(nullable: false),
                    productId = table.Column<int>(nullable: false),
                    beginYear = table.Column<int>(nullable: false),
                    endYear = table.Column<int>(nullable: false),
                    ratio = table.Column<decimal>(type: "decimal(5,2)", nullable: false),
                    signCnt = table.Column<int>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_contract_project", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_contract_project_customerId_productId_signCnt",
                table: "contract_project",
                columns: new[] { "customerId", "productId", "signCnt" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "contract");

            migrationBuilder.DropTable(
                name: "contract_member");

            migrationBuilder.DropTable(
                name: "contract_project");
        }
    }
}
