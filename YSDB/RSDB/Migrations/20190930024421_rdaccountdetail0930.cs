using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class rdaccountdetail0930 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "rdAccountDetail",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    rdDate = table.Column<DateTime>(nullable: false),
                    accNumber = table.Column<string>(maxLength: 20, nullable: false),
                    summary = table.Column<string>(maxLength: 200, nullable: false),
                    debit = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    credit = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    direction = table.Column<string>(maxLength: 10, nullable: false),
                    balance = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    type = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_rdAccountDetail", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "rdAccountDetail");
        }
    }
}
