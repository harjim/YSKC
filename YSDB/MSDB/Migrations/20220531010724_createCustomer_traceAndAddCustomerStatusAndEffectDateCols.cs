using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createCustomer_traceAndAddCustomerStatusAndEffectDateCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "owerId",
                table: "customer",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<DateTime>(
                name: "effectDate",
                table: "customer",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "status",
                table: "customer",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateTable(
                name: "customer_trace",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    customerId = table.Column<int>(nullable: false),
                    status = table.Column<int>(nullable: false),
                    info = table.Column<string>(maxLength: 500, nullable: true),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_customer_trace", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "customer_trace");

            migrationBuilder.DropColumn(
                name: "effectDate",
                table: "customer");

            migrationBuilder.DropColumn(
                name: "status",
                table: "customer");

            migrationBuilder.AlterColumn<int>(
                name: "owerId",
                table: "customer",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);
        }
    }
}
