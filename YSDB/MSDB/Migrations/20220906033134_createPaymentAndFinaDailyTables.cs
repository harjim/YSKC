using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createPaymentAndFinaDailyTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<string>(
                name: "sealType",
                table: "contract",
                maxLength: 20,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 10);

            migrationBuilder.CreateTable(
                name: "checkPayment",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    customerId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    ownerId = table.Column<int>(nullable: false),
                    deptId = table.Column<int>(nullable: false),
                    techId = table.Column<int>(nullable: false),
                    ownerMangerId = table.Column<int>(nullable: false),
                    finaManagerId = table.Column<int>(nullable: false),
                    finaDirectorId = table.Column<int>(nullable: false),
                    checkDate = table.Column<DateTime>(nullable: false),
                    checkInstId = table.Column<int>(nullable: false),
                    checkCnt = table.Column<int>(nullable: false),
                    unitPrice = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    totalAmount = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    checkUsername = table.Column<string>(maxLength: 50, nullable: true),
                    checkPassword = table.Column<string>(maxLength: 30, nullable: true),
                    payType = table.Column<string>(maxLength: 10, nullable: true),
                    paymentFile = table.Column<string>(maxLength: 500, nullable: true),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_checkPayment", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "checkPayment_rd",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    checkPaymentId = table.Column<int>(nullable: false),
                    pname = table.Column<string>(maxLength: 200, nullable: true),
                    rdTitle = table.Column<string>(maxLength: 50, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_checkPayment_rd", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "finaDaily",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    customerId = table.Column<int>(nullable: false),
                    ownerId = table.Column<int>(nullable: false),
                    deptId = table.Column<int>(nullable: false),
                    itemType = table.Column<string>(maxLength: 10, nullable: true),
                    workDate = table.Column<DateTime>(nullable: false),
                    content = table.Column<string>(maxLength: 1000, nullable: false),
                    filepath = table.Column<string>(maxLength: 500, nullable: true),
                    label = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_finaDaily", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "checkPayment");

            migrationBuilder.DropTable(
                name: "checkPayment_rd");

            migrationBuilder.DropTable(
                name: "finaDaily");

            migrationBuilder.AlterColumn<string>(
                name: "sealType",
                table: "contract",
                maxLength: 10,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 20);
        }
    }
}
