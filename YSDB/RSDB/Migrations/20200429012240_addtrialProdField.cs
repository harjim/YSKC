using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addtrialProdField : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "workshopId",
                table: "p_trialProd",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<decimal>(
                name: "auxMaterial",
                table: "p_trialProd",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "fuel",
                table: "p_trialProd",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "gas",
                table: "p_trialProd",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "mainMaterial",
                table: "p_trialProd",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "pos",
                table: "p_trialProd",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "power",
                table: "p_trialProd",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "spare",
                table: "p_trialProd",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "trialGroup",
                table: "p_trialProd",
                maxLength: 100,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "auxMaterial",
                table: "p_trialProd");

            migrationBuilder.DropColumn(
                name: "fuel",
                table: "p_trialProd");

            migrationBuilder.DropColumn(
                name: "gas",
                table: "p_trialProd");

            migrationBuilder.DropColumn(
                name: "mainMaterial",
                table: "p_trialProd");

            migrationBuilder.DropColumn(
                name: "pos",
                table: "p_trialProd");

            migrationBuilder.DropColumn(
                name: "power",
                table: "p_trialProd");

            migrationBuilder.DropColumn(
                name: "spare",
                table: "p_trialProd");

            migrationBuilder.DropColumn(
                name: "trialGroup",
                table: "p_trialProd");

            migrationBuilder.AlterColumn<int>(
                name: "workshopId",
                table: "p_trialProd",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);
        }
    }
}
